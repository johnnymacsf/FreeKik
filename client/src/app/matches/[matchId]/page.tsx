'use client';

import { useRouter } from "next/navigation";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import API from '../../lib/api';

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

export default function MatchPage() {
    const params = useParams();
    const {matchId} = params;
    const [match, setMatch] = useState<Match |null>(null);
    const [formOpen, setFormOpen] = useState(false);

    const handleFormOpen = () => setFormOpen(true);
    const handleFormClose = () => setFormOpen(false);

    useEffect(() => {
        async function fetchMatch() {
            try{
                const {data} = await API.get(`/match/find/${matchId}`);
                setMatch(data);
            }catch(error: any){
                console.error(error);
            }
        }
        fetchMatch();
    }, [matchId]);

    return (
        <div className="bg-white text-black w-full min-h-screen p-4">
            <div className="max-w-2xl mx-auto text-center">
                <h1 className="text-2xl font-bold mb-4">{match?.homeTeam} vs {match?.awayTeam}</h1>
                <p className="text-xl text-black">{match?.matchDate}</p>
                <p className="text-md text-black">{match?.homeScore ?? 0} - {match?.awayScore ?? 0}</p>
                <p className="text-md text-black">{match?.finalResult ? match.finalResult : "Not Finished Yet"}</p>
                <h2 className="text-lg font-semibold mt-4 mb-2">Odds</h2>

                <button className="bg-green-600 text-white px-4 py-2 rounded cursor-pointer hover:bg-green-300 mt-4" onClick={handleFormOpen}>Add Prediction</button>
                {formOpen && (
                    <div className="fixed inset-0 bg-gray-100 flex justify-center items-center z-50">
                        <div className="bg-white p-6 rounded shadow-lg w-96 relative">
                            <button onClick={handleFormClose} className="bg-red-500 top-2 right-2 absolute cursor-pointer hover:bg-red-300">X</button>
                            <h2 className="text-xl font-bold mb-4 text-blue-600">Add Prediction</h2>
                            <form>
                                <label className="block mb-2 font-semibold text-black">Result</label>
                                <select className="border p-2 w-full mb-4 rounded">
                                    <option value={`${match?.homeTeam}-win`}>{match?.homeTeam}</option>
                                    <option value="draw">Draw</option>
                                    <option value={`${match?.awayTeam}-win`}>{match?.awayTeam}</option>
                                </select>
                                <label className="block mb-2 font-semibold text-black">Points Bet</label>
                                <input type="number" placeholder="Points" className="border p-2 w-full mb-4 rounded"/>
                                <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-300">Submit</button>
                            </form>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}