import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    host: true
  },
  build: {
    // Ensure environment variables are available at build time
    rollupOptions: {
      output: {
        manualChunks: undefined
      }
    }
  },
  // Define environment variables that should be available in the client
  define: {
    'process.env.VITE_API_BASE_URL': JSON.stringify(process.env.VITE_API_BASE_URL)
  }
})
