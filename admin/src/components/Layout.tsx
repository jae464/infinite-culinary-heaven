import styled from 'styled-components';
import { Header } from '../components/Header';
import { Sidebar } from '../components/Sidebar';
import { ContestListComponent } from '../components/ContestList';
import { ReactElement } from 'react';

const AppContainer = styled.div`
  display: flex;
  height: 100vh;
  overflow: hidden;
`;

const MainContent = styled.div`
  flex: 1;
  width: 100%;
  background-color: #f7f9fc;
  display: flex;
  flex-direction: column;
`;

interface LayoutProps {
  children: ReactElement;
}

export const Layout = ({ children }: LayoutProps) => {
  return (
    <>
      <AppContainer>
        <Sidebar />
        <MainContent>
          <Header />
          {children}
        </MainContent>
      </AppContainer>
    </>
  );
};
