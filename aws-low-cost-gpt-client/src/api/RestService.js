import axios from "axios";

const API_ENDPOINT = process.env.VUE_APP_API_GATEWAY_ENDPOINT;

function getAxiosConfig(method, path, data = null) {
  const accessToken = localStorage.getItem("access-token");
  const token = localStorage.getItem("user-token");
  const email = localStorage.getItem("user-mail-address");

  const url = `${API_ENDPOINT}${path}`;

  return {
    method: method,
    url: url,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
      "user-mail-address": email,
      "access-token": accessToken,
    },
    data: data,
  };
}

export const getData = (path) => {
  return axios(getAxiosConfig("get", path))
    .then((response) => response.data)
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
};

export const postData = (path, data) => {
  return axios(getAxiosConfig("post", path, data))
    .then((response) => response.data)
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
};

export const putData = (path, data) => {
  return axios(getAxiosConfig("put", path, data))
    .then((response) => response.data)
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
};

export const patchData = (path, data) => {
  return axios(getAxiosConfig("patch", path, data))
    .then((response) => response.data)
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
};

export const deleteData = (path) => {
  return axios(getAxiosConfig("delete", path))
    .then((response) => response.data)
    .catch((error) => {
      console.error("Error:", error);
      throw error;
    });
};
