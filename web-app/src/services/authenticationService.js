import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";

export const logIn = async (email, password) => {
  const response = await httpClient.post(API.LOGIN, {
    email: email,
    password: password,
  });
  setToken(response.data?.data?.token);

  return response;
};

export const logOut = () => {
  removeToken();
};

export const isAuthenticated = () => {
  return getToken();
};
