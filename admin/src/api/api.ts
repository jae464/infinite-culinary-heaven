import axios from 'axios';

const api = axios.create({
  // baseURL: 'http://localhost:8080', // API 서버 주소
  baseURL: 'http://52.78.188.164:8080', // API 서버 주소
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

export const getImageApi = async () => {
  const res = await api.get(`/images`, {
    params: { image: '17663c4b-1a5f-4533-8b0d-54ce6a5d7a15_1112356.jpg' },
    responseType: 'blob',
  });
  console.log(res);
  return URL.createObjectURL(res.data);
};

export const createTopicIngredient = async (
  name: string,
  image: File,
  accessToken: string,
) => {
  const formData = new FormData();
  formData.append('image', image); // 이미지 파일 추가
  formData.append(
    'request',
    new Blob([JSON.stringify({ name })], { type: 'application/json' }),
  );

  const response = await api.post('/topic-ingredients', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
      Authorization: `Bearer ${accessToken}`,
    },
  });

  console.log(response.data);
  return response.data; // 생성된 주재료 데이터 반환
};

export const reissueToken = async (refreshToken: string) => {
  const response = await api.post('/reissue', { refreshToken });
  return response.data; // ReissueResponse 반환
};
