import axios from "axios";

const API = "http://localhost:8080";

export const getHealth = () => axios.get(`${API}/system/health`);
export const fixIssue = (id) => axios.post(`${API}/system/fix/${id}`);