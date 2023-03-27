import AuthHeader from './AuthHeader';
import axios from './axios';

const postWithoutAuth = (url, body) => {
  const request = axios.post(url, body);
  return request.then((response) => response.data);
};

const getWithAuth = (url) => {
  const request = axios.get(url, { headers: AuthHeader() });
  return request.then((response) => response.data);
};

const postWithAuth = (url, body) => {
  const request = axios.post(url, body, { headers: AuthHeader() });
  return request.then((response) => response.data);
};

const putWithAuth = (url, body) => {
  const request = axios.put(url, body, { headers: AuthHeader() });
  return request.then((response) => response.data);
};

const deleteWithAuth = (url) => {
  const request = axios.delete(url, { headers: AuthHeader() });
  return request.then((response) => response.data);
};

const HttpService = {
  postWithoutAuth,
  getWithAuth,
  postWithAuth,
  putWithAuth,
  deleteWithAuth,
};

export default HttpService;
