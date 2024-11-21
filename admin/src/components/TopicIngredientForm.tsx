import React, { useState } from 'react';
import styled from 'styled-components';
import { createTopicIngredient } from '../api/api';
import { useAuth } from '../contexts/AuthContext';

const FormContainer = styled.div`
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const Title = styled.h2`
  margin-bottom: 20px;
  color: #1d3557;
`;

const InputField = styled.input`
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;

  &:focus {
    outline: none;
    border-color: #457b9d;
    box-shadow: 0 0 4px rgba(69, 123, 157, 0.4);
  }
`;

const FileInput = styled.input`
  margin-bottom: 15px;
`;

const SubmitButton = styled.button`
  width: 100%;
  padding: 10px;
  background-color: #457b9d;
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #1d3557;
  }
`;

const SuccessMessage = styled.div`
  color: green;
  margin-top: 15px;
`;

const ErrorMessage = styled.div`
  color: red;
  margin-top: 15px;
`;

export const TopicIngredientForm: React.FC = () => {
  const [name, setName] = useState<string>('');
  const [image, setImage] = useState<File | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const { accessToken } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!name || !image) {
      setErrorMessage('주재료 이름과 이미지를 모두 입력해주세요.');
      return;
    }

    if (accessToken == null) {
      setErrorMessage('Access Token이 만료되었습니다.');
      return;
    }

    try {
      const response = await createTopicIngredient(name, image, accessToken);
      setSuccessMessage(`주재료가 생성되었습니다: ${response.name}`);
      setErrorMessage(null);
      setName('');
      setImage(null);
    } catch (error) {
      setErrorMessage('주재료 생성 중 오류가 발생했습니다.');
      setSuccessMessage(null);
    }
  };

  return (
    <FormContainer>
      <Title>주재료 생성</Title>
      {successMessage && <SuccessMessage>{successMessage}</SuccessMessage>}
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
      <form onSubmit={handleSubmit}>
        <InputField
          type="text"
          placeholder="주재료 이름"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <FileInput
          type="file"
          accept="image/*"
          onChange={(e) => {
            if (e.target.files && e.target.files[0]) {
              setImage(e.target.files[0]);
            }
          }}
        />
        <SubmitButton type="submit">생성</SubmitButton>
      </form>
    </FormContainer>
  );
};
