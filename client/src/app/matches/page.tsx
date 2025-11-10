'use client';

import { useEffect, useState } from "react";
import MatchesCard from "../components/MatchesCard";
import API from '../lib/api';

interface Match{
    matchId: string;
    homeTeam: string;
    awayTeam: string;
    homeScore: number | null;
    awayScore: number | null;
    finalResult: string | null;
    matchDate: string;
    book: {
        book: Record<string, Odds>;
    };
}

interface Odds{
    betType: string;
    lastUpdate: string;
    outcomes: Record<string, number>;
}

export default function MatchesPage() {
    const [matches, setMatches] = useState<Match[]>([]);

    useEffect(()=>{
        async function fetchMatches() {
            try{
                const {data} = await API.get('/match/findAll');
                setMatches(data);
            }catch(error: any){
                console.error(error)
            }
        }
        fetchMatches();
    }, []);
    return(
        <div className="bg-white text-black w-full min-h-screen p-4">
            <h1 className="text-2xl font-bold mb-4">Matches</h1>
            {matches.map(match => (
                <MatchesCard key={match.matchId} homeTeam={match.homeTeam} awayTeam={match.awayTeam} date={match.matchDate} matchId={match.matchId}/>
            ))}
        </div>
    )
}