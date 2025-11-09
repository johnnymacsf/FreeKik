'use client';

import { useState } from 'react';
import API from '../lib/api';

export default function LoginForm({
  onSuccess,
  onClose,
}: {
  onSuccess: () => void;
  onClose: () => void;
}) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      const response = await API.post('/auth/login', { username, password });
      const token = response.headers['authorization']?.split(' ')[1];
      if(token){
        localStorage.setItem('token', token);
        console.log("JWT stored as: ", token);
      }else{
        console.log("JWT not found");
      }
      onSuccess();
      onClose();
      alert('Successfully logged in');
    } catch (error: any) {
        console.error("login error: ", error.response?.data || error.message);
      alert('Login failed');
    }
  }

  return (
    <div className="fixed inset-0 flex justify-center items-center bg-gray-400">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded shadow w-80 text-black"
      >
        <h2 className="text-xl mb-4 text-center font-bold">Login</h2>
        <input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Username"
          className="border p-2 mb-2 w-full"
        />
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Password"
          type="password"
          className="border p-2 mb-2 w-full"
        />
        <button className="bg-blue-600 text-white px-4 py-2 rounded w-full hover:bg-green-400">
          Login
        </button>
      </form>
    </div>
  );
}