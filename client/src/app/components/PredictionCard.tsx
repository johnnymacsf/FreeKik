'use client';

interface PredictionCardProps {
    predictionId: number;
    matchId: string;
    homeTeam: string;
    awayTeam: string;
    resultPrediction: string;
    pointsBet: number;
    predictionOdds: number;
    pointsResult: number;
    correctPrediction: boolean;
    finalResult?: string;
    dateMade: string;
}

export default function PredictionCard({ predictionId, matchId, homeTeam, awayTeam, resultPrediction, pointsBet, predictionOdds, pointsResult, correctPrediction, finalResult, dateMade} : PredictionCardProps){
    return (
        <div className="border rounded-lg p-4 shadow-sm bg-gray-50 mb-4">
            <h2>{homeTeam} vs {awayTeam}</h2>
            <p>Prediction made: {new Date(dateMade.slice(0,10)).toLocaleDateString()}</p>
            <p>Prediction: {resultPrediction}</p>
            <p>Points bet: {pointsBet}</p>
            
        </div>
    )
}