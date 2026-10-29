import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom"
import Header from "../widgets/header/Header.tsx"
import Footer from "../widgets/footer/Footer.tsx"
import styles from "./App.module.scss"
import "./global.css"
import "./colours.css"
import Operations from "../pages/operations/Operations.tsx";
import Balances from "../pages/balances/Balances.tsx";
import Authorisation from "../pages/authorisation/Authorisation.tsx";
import Articles from "../pages/articles/Articles.tsx";

function App() {
  return (
      <BrowserRouter>
          <div className={styles.bg}>
              <Header/>
              <div className="container page">
                  <Routes>
                      <Route path="/operations" element={<Operations/>}></Route>
                      <Route path="/balances" element={<Balances/>}></Route>
                      <Route path="/articles" element={<Articles/>}></Route>
                      <Route path="/auth" element={<Authorisation/>}></Route>
                      <Route path="*" element={<Navigate to="/operations"/>}></Route>
                  </Routes>
              </div>
              <Footer/>
          </div>
      </BrowserRouter>
  )
}

export default App
