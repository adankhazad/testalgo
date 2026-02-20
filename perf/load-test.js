import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';


const BASE_URL = 'https://reqres.in';
const ENV_NAME = 'local';
const API_PATH = '/api/users?page=2';


const errorRate = new Rate('custom_error_rate');

export const options = {
  scenarios: {
    constant_load: {
      executor: 'constant-vus',
      vus: 100,
      duration: '5m',
      gracefulStop: '30s',
    },
  },
  thresholds: {
    http_req_duration: ['p(50)<300', 'p(95)<800', 'p(99)<1500'],
    http_req_failed: ['rate<0.05'],
    custom_error_rate: ['rate<0.06'],
    http_reqs: ['rate>=90'],
  },
  summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(50)', 'p(90)', 'p(95)', 'p(99)'],
};

export function setup() {
  return { baseURL: BASE_URL };
}



 export default function (data) {
   const res = http.get(`${data.baseURL}${API_PATH}`, {
     headers: {
       Accept: 'application/json',
       'x-api-key': __ENV.REQRES_API_KEY
     },
     tags: { name: 'get_users' },
     timeout: '10s',
   });

   const ok = check(res, {
     'status is 200':      (r) => r.status === 200,
     'body not empty':     (r) => (r.body || '').length > 0,
     'duration < 2s':      (r) => r.timings.duration < 2000,
   });

   errorRate.add(!ok);

   sleep(1);
 }


export function teardown(data) {
    if (data) {
        console.log('Test accomplished. BASE URL : ${data.baseURL}');
        }
    }
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";

export function handleSummary(data) {
  return {
    "report.html": htmlReport(data)
  };
}
