import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom"
import { Header } from "@/widgets/header"
import { Footer } from "@/widgets/footer"
import styles from "./App.module.scss"
import "./global.css"
import "./colours.css"
import {OperationsPage} from "@/pages/operations";
import {BalancesPage} from "@/pages/balances";
import {AuthorisationPage} from "@/pages/authorisation";
import {ArticlesPage} from "@/pages/articles";
import {AuthProvider} from "@/entities/auth";
import {ReportsPage} from "@/pages/reports";

function App() {
  return (
      <AuthProvider>
          <BrowserRouter>
              <div className={styles.bg}>
                  <Header/>
                  <div className="container page">
                      <Routes>
                          <Route path="/operations" element={<OperationsPage/>}></Route>
                          <Route path="/balances" element={<BalancesPage/>}></Route>
                          <Route path="/articles" element={<ArticlesPage/>}></Route>
                          <Route path="/reports" element={<ReportsPage/>}></Route>
                          <Route path="/auth" element={<AuthorisationPage/>}></Route>
                          <Route path="*" element={<Navigate to="/operations"/>}></Route>
                      </Routes>
                  </div>
                  <Footer/>
              </div>
          </BrowserRouter>
      </AuthProvider>
  )
}

export default App
