export interface TopicIngredient {
  id: number;
  name: string;
  image: string;
}

export interface ContestResponse {
  id: number;
  description: string;
  startDate: string; // ISO 8601 string
  endDate: string; // ISO 8601 string
  topicIngredient: TopicIngredient;
}

export interface ContestsResponse {
  contests: ContestResponse[];
}

export interface TopicIngredientResponse {
  id: number;
  name: string;
  image: string;
}

export interface TopicIngredientsResponse {
  topicIngredients: TopicIngredientResponse[];
}
