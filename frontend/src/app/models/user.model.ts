export interface User {
  id: string;
  roles: Role[];
  email: string;
  token: string;
}

export interface AccessToken {
  exp: number;
  roles: string;
  sub: string;
  userId: string;
}

export enum Role {
  ROLE_ADMIN = 'ROLE_ADMIN',
  ROLE_MODERATOR = 'ROLE_MODERATOR',
  ROLE_USER = 'ROLE_USER',
}

export interface AnyUserExistsRequest {
  email?: string;
  phoneNumber?: string;
}

export interface UserContactResponse {
  id: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
}
