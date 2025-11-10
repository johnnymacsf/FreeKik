'use client';

interface MatchesCardProps {
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

export default function MatchesCard({ homeTeam, awayTeam, homeScore, awayScore, date, odds}: MatchesCardProps){
    return (
        <div className="border rounded-lg p-4 mb-4 shadow-sm bg-gray-50">
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