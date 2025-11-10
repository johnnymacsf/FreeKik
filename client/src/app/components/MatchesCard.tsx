'use client';

import { useRouter } from "next/navigation";

interface MatchesCardProps {
    matchId: string;
    homeTeam: string;
    awayTeam: string;
    homeScore?: string;
    awayScore ?: string;
    result ?: string;
    date: string;
    odds?:{
        [key:string]:number;
    }
};

export default function MatchesCard({matchId, homeTeam, awayTeam, homeScore, awayScore, date, odds}: MatchesCardProps){
    const router = useRouter();

    const handleClick = () => {
        router.push(`/matches/${matchId}`);
    };

    return (
        <div className="border rounded-lg p-4 mb-4 shadow-sm bg-gray-50 cursor-pointer hover:bg-blue-300" onClick={handleClick}>
            <div className="flex justify-between items-center">
                <div>
                    <h2 className="font-bold text-lg">{homeTeam} vs {awayTeam}</h2>
                    <p className="text-md text-black">{new Date(date).toLocaleDateString()}</p>
                    <p className="text-md text-black">{homeScore ?? 0} - {awayScore ?? 0}</p>
                </div>
            </div>
        </div>
    )
}