import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { LoginForm } from './components/LoginForm';
import { PrivateRoute } from './components/PrivateRoute';
import { ContestPage } from './pages/ContestPage';
import { TopicIngredientPage } from './pages/TopicIngredientPage';
import { HomePage } from './pages/HomePage';

export const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/" element={<PrivateRoute />}>
            <Route path="/" element={<HomePage />} />
            <Route path="/contest" element={<ContestPage />} />
            <Route path="/topic-ingredient" element={<TopicIngredientPage />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
};
