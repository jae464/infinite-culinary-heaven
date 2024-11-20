import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { getTopicIngredients, createTopicIngredient } from '../api/api';
import { TopicIngredientResponse } from '../type/api/Contest';
import { Modal } from '../components/CustomModal';
import { useAuth } from '../contexts/AuthContext';

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

const Button = styled.button`
  margin-left: auto;
  padding: 10px 15px;
  background-color: #457b9d;
  color: #ffffff;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    background-color: #1d3557;
  }
`;

const InputField = styled.input`
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;

  &:focus {
    outline: none;
    border-color: #457b9d;
    box-shadow: 0 0 4px rgba(69, 123, 157, 0.4);
  }
`;

export const TopicIngredientList: React.FC = () => {
  const [ingredients, setIngredients] = useState<TopicIngredientResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newName, setNewName] = useState<string>('');
  const [newImage, setNewImage] = useState<File | null>(null);
  const { accessToken } = useAuth();

  useEffect(() => {
    const fetchIngredients = async () => {
      try {
        const data = await getTopicIngredients(0, 10);
        setIngredients(data.topicIngredients);
      } catch (err) {
        setError('Failed to load topic ingredients.');
      }
    };

    fetchIngredients();
  }, []);

  const handleCreate = async () => {
    if (!newName || !newImage) {
      setError('주재료 이름과 이미지를 모두 입력해주세요.');
      return;
    }

    if (!accessToken) {
      setError('Access token이 필요합니다. 다시 로그인하세요.');
      return;
    }

    try {
      const response = await createTopicIngredient(
        newName,
        newImage,
        accessToken,
      );
      setIngredients((prev) => [...prev, response]); // 새 항목 추가
      setIsModalOpen(false); // Modal 닫기
      setNewName('');
      setNewImage(null);
    } catch (err) {
      setError('주재료 생성 중 오류가 발생했습니다.');
    }
  };

  return (
    <Container>
      <div
        style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}
      >
        <Title>주재료 목록</Title>
        <Button onClick={() => setIsModalOpen(true)}>생성</Button>
      </div>

      {error && <ErrorText>{error}</ErrorText>}

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

      {isModalOpen && (
        <Modal title="주재료 생성" onClose={() => setIsModalOpen(false)}>
          <InputField
            type="text"
            placeholder="주재료 이름"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
          />
          <InputField
            type="file"
            accept="image/*"
            onChange={(e) => {
              if (e.target.files && e.target.files[0]) {
                setNewImage(e.target.files[0]);
              }
            }}
          />
          <Button onClick={handleCreate}>생성</Button>
        </Modal>
      )}
    </Container>
  );
};
