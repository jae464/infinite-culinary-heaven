import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const LoginContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f7f9fc;
`;

const LoginBox = styled.div`
  width: 100%;
  max-width: 400px;
  background-color: #ffffff;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
`;

const Title = styled.h2`
  margin-bottom: 20px;
  font-size: 24px;
  color: #1d3557;
`;

const InputField = styled.input`
  width: 100%;
  padding: 10px 15px;
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

const LoginButton = styled.button`
  width: 100%;
  padding: 10px 15px;
  background-color: #457b9d;
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  &:hover {
    background-color: #1d3557;
  }
`;

const AdditionalLinks = styled.div`
  margin-top: 15px;
  font-size: 14px;
  color: #666;

  a {
    color: #457b9d;
    text-decoration: none;
    &:hover {
      text-decoration: underline;
    }
  }
`;

const ErrorText = styled.div`
  color: red;
  font-size: 14px;
  margin-bottom: 15px;
`;

export const LoginForm: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!email || !password) {
      setError('이메일과 비밀번호를 입력하세요.');
      return;
    }

    console.log('로그인 요청:', { email, password });
    await login(email, password);
    navigate('/');
    setError(null);
  };

  return (
    <LoginContainer>
      <LoginBox>
        <Title>로그인</Title>
        {error && <ErrorText>{error}</ErrorText>}
        <form onSubmit={handleLogin}>
          <InputField
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <InputField
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <LoginButton type="submit">로그인</LoginButton>
        </form>
      </LoginBox>
    </LoginContainer>
  );
};
