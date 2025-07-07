export interface ConversationStarter {
  prompt: string;
}

export interface ConversationStarterCollection {
  conversationsStarters: ConversationStarter[];
}

export interface MatchStatus {
  userID: string;
  status: 'UNSENT' | 'SENT' | 'CONFIRMED' | 'REJECTED' | 'EXPIRED';
}

export interface Group {
  groupID: string;
  date: string;
  time: number;
  location: 'GARCHING' | 'ARCISSTR';
  userStatus: MatchStatus[];
  conversationStarters: ConversationStarterCollection;
}

export interface Match {
  matchID: string;
  userID: string;
  status: 'UNSENT' | 'SENT' | 'CONFIRMED' | 'REJECTED' | 'EXPIRED';
  group: Group;
}

export interface MatchesResponse {
  matches: Match[];
} 