import React, { useEffect, useState } from 'react';
import { getContests, getImageApi } from '../api/api';
import { ContestResponse } from '../type/api/Contest';
import styled from 'styled-components';

const Container = styled.div`
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const Title = styled.h2`
  margin-bottom: 20px;
`;

const ContestList = styled.ul`
  list-style: none;
  padding: 0;
`;

const ContestItem = styled.li`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 15px;
  border-bottom: 1px solid #ddd;

  &:last-child {
    border-bottom: none;
  }
`;

const ContestDetails = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
`;

const IngredientImage = styled.img`
  width: 50px;
  height: 50px;
  border-radius: 8px;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
`;

const ContestDate = styled.span`
  font-size: 12px;
  color: #666;
`;

const ErrorText = styled.div`
  color: red;
  margin-top: 20px;
`;

export const ContestListComponent: React.FC = () => {
  const [contests, setContests] = useState<ContestResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [image, setImage] = useState<string>('null');

  useEffect(() => {
    const fetchContests = async () => {
      try {
        const data = await getContests(0, 10);
        const image = await getImageApi();
        setImage(image);
        console.log(image);
        setContests(data.contests);
      } catch (err) {
        setError('Failed to load contests.');
      }
    };

    fetchContests();
  }, []);

  if (error) {
    return <ErrorText>{error}</ErrorText>;
  }

  return (
    <Container>
      <Title>대회 목록</Title>
      <ContestList>
        {contests.map((contest) => (
          <ContestItem key={contest.id}>
            <ContestDetails>
              <IngredientImage src={image} alt={contest.topicIngredient.name} />
              <Description>
                <strong>{contest.description}</strong>
                <ContestDate>
                  {new Date(contest.startDate).toLocaleDateString()} -{' '}
                  {new Date(contest.endDate).toLocaleDateString()}
                </ContestDate>
              </Description>
            </ContestDetails>
          </ContestItem>
        ))}
      </ContestList>
    </Container>
  );
};
