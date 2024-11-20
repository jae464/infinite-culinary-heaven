import React from 'react';
import styled from 'styled-components';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

const HeaderContainer = styled.div`
  height: 60px;
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const NavMenu = styled.div`
  display: flex;
  gap: 20px;
  font-size: 16px;
  color: #333;
`;

const UserProfile = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

const ProfileImage = styled.div`
  width: 40px;
  height: 40px;
  background-color: #ccc;
  border-radius: 50%;
`;

const UserName = styled.div`
  font-size: 14px;
  color: #333;
`;

const LogoutButton = styled.button`
  background: none;
  border: none;
  color: #333;
  font-size: 14px;
  cursor: pointer;
  &:hover {
    text-decoration: underline;
  }
`;

export const Header: React.FC = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };
  return (
    <HeaderContainer>
      <NavMenu></NavMenu>
      <UserProfile>
        <UserName>admin</UserName>
        <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
      </UserProfile>
    </HeaderContainer>
  );
};
