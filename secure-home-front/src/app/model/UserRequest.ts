export interface UserRequest{
    id?: number;
	username: string;
	password: string;
	firstname: string;
	lastname: string;
	email: string;
	roles?: string[];
}
    