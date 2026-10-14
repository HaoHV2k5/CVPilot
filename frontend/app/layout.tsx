import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "CVPilot - ResultMatch Resume AI",
  description: "Nền tảng chat AI giúp định vị CV so với thị trường việc làm AI/IT",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="vi">
      <body className="antialiased min-h-screen bg-slate-950 text-slate-100">
        {children}
      </body>
    </html>
  );
}
