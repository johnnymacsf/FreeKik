'use client';

import { useState } from 'react';
import API from '../lib/api';

export default function SignUpForm({
  onClose,
  onShowLogin
}: {
  onClose: () => void;
  onShowLogin: () => void;
}) {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    async function handleSubmit(e: React.FormEvent){
        e.preventDefault();
        try{
            const {data} = await API.post('/auth/register', {email, username, password});
            alert("Successfully signed up!");
            onClose();
            onShowLogin();
        }catch(error: any){
            console.error(error);
            alert("Unsuccessful sign up");
        }
    }

    return (
        <div className="fixed inset-0 flex justify-center items-center bg-gray-400">
            <form
                onSubmit={handleSubmit}
                className="bg-white p-6 rounded shadow w-80 text-black"
            >
                <h2 className="text-xl mb-4 text-center font-bold">Sign Up</h2>
                <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Your email"
                className="border p-2 mb-2 w-full"
                type="email"
                />
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
            </form>`
    </div>
    )
}