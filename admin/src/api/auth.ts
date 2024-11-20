import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // API 서버 주소
});

export const loginAPI = async (id: string, password: string) => {
  console.log('loginAPI');
  const response = await api.post('/auth/login/admin', { id, password });
  console.log(response);
  return response.data; // { accessToken: string }
};

export const getContests = async (page: number, size: number) => {
  const response = await api.get(`/contests`, {
    params: { page, size },
  });
  return response.data; // ContestsResponse 타입 데이터 반환
};

export const getTopicIngredients = async (page: number, size: number) => {
  const response = await api.get(`/topic-ingredients`, {
    params: { page, size },
  });
  return response.data; // TopicIngredientsResponse 타입 데이터 반환
};
