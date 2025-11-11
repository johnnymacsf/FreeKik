'use client';

import { useEffect, useState } from "react";
import API from "../lib/api";
import PredictionCard from "../components/PredictionCard";
interface Prediction {
    predictionId: number;
    matchId: string;
    homeTeam: string;
    awayTeam: string;
    result_prediction: string;
    pointsBet: number;
    prediction_odds: number;
    pointsResult: number;
    correctPrediction: boolean;
    finalResult?: string;
    date: string;
}

export default function PredictionsPage() {
    const [predictions, setPredictions] = useState<Prediction[]>([]);

    useEffect(() => {
        async function fetchPredictions() {
            try{
                const userId = localStorage.getItem("userId");
                const token = localStorage.getItem("token");
                const {data} = await API.get(`/prediction/find/user/${userId}`);
                setPredictions(data);
            }catch(error: any){
                console.error(error);
            }
        }
        fetchPredictions();
    }, []);
    return(
        <div className="bg-white text-black w-full min-h-screen">
            <h1 className="text-2xl font-bold mb-4">Your Predictions</h1>
            {predictions.map(prediction => (
                <PredictionCard key={prediction.predictionId} 
                    homeTeam={prediction.homeTeam} awayTeam={prediction.awayTeam} dateMade={prediction.date} 
                    matchId={prediction.matchId} predictionId={prediction.predictionId} predictionOdds={prediction.prediction_odds} 
                    resultPrediction={prediction.result_prediction}
                    pointsBet={prediction.pointsBet}
                    finalResult={prediction.finalResult}
                    pointsResult={prediction.pointsResult}
                    correctPrediction={prediction.correctPrediction}
                />
            ))}
        </div>
    )
}