import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getTopicIngredients } from '../api/auth';
import { TopicIngredientResponse } from '../type/api/Contest';

const Container = styled.div`
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const Title = styled.h2`
  margin-bottom: 20px;
`;

const IngredientList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
`;

const IngredientItem = styled.li`
  display: flex;
  align-items: center;
  padding: 15px 10px;
  border-bottom: 1px solid #ddd;

  &:last-child {
    border-bottom: none;
  }
`;

const IngredientImage = styled.img`
  width: 60px;
  height: 60px;
  border-radius: 8px;
  margin-right: 15px;
  object-fit: cover;
`;

const IngredientDetails = styled.div`
  display: flex;
  flex-direction: column;
`;

const IngredientName = styled.span`
  font-size: 16px;
  font-weight: bold;
  color: #333;
`;

const ErrorText = styled.div`
  color: red;
  margin-top: 20px;
`;

export const TopicIngredientList: React.FC = () => {
  const [ingredients, setIngredients] = useState<TopicIngredientResponse[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchIngredients = async () => {
      try {
        console.log('fetchIngredients');
        const data = await getTopicIngredients(0, 10); // 0페이지, 10개 데이터 요청
        setIngredients(data.topicIngredients);
      } catch (err) {
        setError('Failed to load topic ingredients.');
      }
    };

    fetchIngredients();
  }, []);

  if (error) {
    return <ErrorText>{error}</ErrorText>;
  }

  return (
    <Container>
      <Title>주재료 목록</Title>
      <IngredientList>
        {ingredients.map((ingredient) => (
          <IngredientItem key={ingredient.id}>
            <IngredientImage src={ingredient.image} alt={ingredient.name} />
            <IngredientDetails>
              <IngredientName>{ingredient.name}</IngredientName>
            </IngredientDetails>
          </IngredientItem>
        ))}
      </IngredientList>
    </Container>
  );
};
