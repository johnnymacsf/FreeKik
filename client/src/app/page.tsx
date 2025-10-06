import Image from "next/image";

export default function Home() {
  return (
    <div className="flex flex-col justify-center items-center min-h-screen w-full text-4xl bg-white">
      <h1 className="text-3xl font-semibold text-blue-400">Welcome to FreeKik</h1>
      <p className = "mt-4 text-blue-400">Login or sign up to get started!</p>
    </div>
  );
}
