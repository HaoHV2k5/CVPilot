export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-8 bg-gradient-to-b from-slate-950 via-slate-900 to-slate-950">
      <div className="z-10 max-w-5xl w-full items-center justify-between text-center">
        <div className="inline-block px-4 py-1.5 mb-6 text-sm font-semibold tracking-wide text-cyan-400 bg-cyan-950/60 rounded-full border border-cyan-800/50 backdrop-blur-md">
          ResultMatch Resume AI
        </div>
        <h1 className="text-4xl sm:text-6xl font-extrabold tracking-tight text-white mb-6">
          Định vị CV của bạn với <span className="bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent">CVPilot</span>
        </h1>
        <p className="max-w-2xl mx-auto text-lg text-slate-300 mb-10 leading-relaxed">
          Trả lời câu hỏi – Phân tích thị trường AI, IT – Gợi ý lộ trình cá nhân hóa dựa trên dữ liệu tuyển dụng thực tế.
        </p>

        <div className="flex flex-wrap items-center justify-center gap-4">
          <a
            href="/chat"
            className="px-6 py-3 rounded-xl bg-gradient-to-r from-cyan-500 to-blue-600 text-white font-medium hover:from-cyan-400 hover:to-blue-500 shadow-lg shadow-cyan-500/20 transition-all duration-200"
          >
            Bắt đầu hỏi đáp thị trường
          </a>
          <a
            href="/upload"
            className="px-6 py-3 rounded-xl bg-slate-800/80 hover:bg-slate-800 text-slate-200 font-medium border border-slate-700/80 transition-all duration-200"
          >
            Phân tích CV ngay
          </a>
        </div>
      </div>
    </main>
  );
}
