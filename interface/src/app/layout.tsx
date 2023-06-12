'use client'
import './globals.css'
import { createTheme, ThemeProvider} from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});
export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <html lang="en">
          <body>{children}</body>
      </html>
    </ThemeProvider>
  )
}
