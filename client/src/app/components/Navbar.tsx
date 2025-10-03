'use client';

import { useState, useEffect } from 'react';
import LoginForm from './LoginForm';
import SignUpForm from './SignUpForm';
import { useRouter } from 'next/navigation';

export default function Navbar() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showLoginForm, setShowLoginForm] = useState(false);
  const [showSignUpForm, setShowSignUpForm] = useState(false);
  const router = useRouter();

  useEffect(() => {
    setIsLoggedIn(!!localStorage.getItem('token'));
  }, []);

  function handleLogout() {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    alert('Logged out successfully');
    router.push('/');
  }

  return (
    <div className="p-4 bg-blue-600 text-white flex justify-between items-center relative">
      <h1 className="text-4xl font-bold">FreeKik</h1>
      <button className="text-3xl" onClick={() => setMenuOpen(!menuOpen)}>
        ☰
      </button>

      {menuOpen && (
        <div className="absolute right-4 top-14 bg-white text-black rounded shadow p-4">
          {isLoggedIn ? (
            <>
              <button className='block w-full mb-2' onClick={() => router.push('/matches')}>
                Matches
              </button>
              <button className='block w-full mb-2' onClick={() => router.push('/user-stats')}>
                User Stats
              </button>
              <button className='block w-full mb-2' onClick={() => router.push('/predictions')}>
                Predictions
              </button>
              <button className="block w-full mb-2" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            <>
              <button
                className="block w-full mb-2"
                onClick={() => {
                  setShowLoginForm(true);
                  setMenuOpen(false);
                }}
              >
                Login
              </button>
              <button className="block w-full mb-2"
                onClick={() => {
                  setShowSignUpForm(true);
                  setMenuOpen(false);
                }}
              >
                Sign Up
              </button>
            </>
          )}
        </div>
      )}

      {showLoginForm && (
        <LoginForm
          onSuccess={() => setIsLoggedIn(true)}
          onClose={() => setShowLoginForm(false)}
        />
      )}

      {showSignUpForm && (
        <SignUpForm 
          onClose={() => setShowSignUpForm(false)}
          onShowLogin={() => setShowLoginForm(true)}
        />
      )}
    </div>
  );
}