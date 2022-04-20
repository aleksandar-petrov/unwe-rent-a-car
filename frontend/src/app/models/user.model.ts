export interface User {
  id: string;
  roles: string[];
  email: string;
  token: string;
}

export interface AccessToken {
  exp: number;
  roles: string;
  sub: string;
  userId: string;
}
