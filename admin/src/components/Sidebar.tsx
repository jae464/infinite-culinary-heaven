import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const SidebarContainer = styled.div`
  width: 240px;
  background-color: #1d3557;
  color: #ffffff;
  display: flex;
  flex-direction: column;
`;

const Logo = styled.div`
  padding: 20px;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  background-color: #457b9d;
`;

const MenuList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
  flex: 1;
`;

const MenuItem = styled.li`
  padding: 15px 20px;
  font-size: 16px;
  cursor: pointer;
  &:hover {
    background-color: #2c5373;
  }
`;

const SidebarFooter = styled.div`
  padding: 20px;
  font-size: 14px;
  background-color: #1a2739;
  text-align: center;
`;

export const Sidebar: React.FC = () => {
  const navigate = useNavigate();

  const handleNavigation = (path: string) => {
    navigate(path);
  };

  return (
    <SidebarContainer>
      <Logo>Infinite Culinary</Logo>
      <MenuList>
        <MenuItem onClick={() => handleNavigation('/contest')}>
          대회관리
        </MenuItem>
        <MenuItem onClick={() => handleNavigation('/topic-ingredient')}>
          재료등록
        </MenuItem>
      </MenuList>
    </SidebarContainer>
  );
};
