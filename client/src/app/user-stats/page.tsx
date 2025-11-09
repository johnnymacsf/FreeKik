'use client';
import {useRouter } from 'next/navigation';
import {useEffect, useState} from 'react';
import API from "../lib/api";

interface UserStats{
    points: number;
    wins: number;
    losses: number;
    username: string;
}

export default function UserStatsPage(){
    const router = useRouter();
    const [stats, setStats] = useState<UserStats | null>(null);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
          router.push('/login');
          return;
        }

        const getStats = async () => {
          try {
            const { data } = await API.get('/user/stats');
            setStats(data);
          } catch (error: any) {
            console.error('Error fetching stats:', error);
          }
        };

        getStats();
      }, [router]);

    return(
        <div className="flex flex-col items-center bg-gray-100 text-black w-full min-h-screen">
                {stats ? (
                    <>
                        <h1 className="text-4xl text-blue-600 font-bold mb-4 mt-4">{stats.username}'s Stats</h1>
                        <p className="text-2xl">Wins: {stats.wins}</p>
                        <p className="text-2xl">Losses: {stats.losses}</p>
                        <p className="text-2xl">Points: {stats.points}</p>
                    </>
                    ) : (
                        <p className="text-2xl">No Stats Found</p>
                    )
                    }
        </div>
    )
}