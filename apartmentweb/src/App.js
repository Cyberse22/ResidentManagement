import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import Home from './components/Home/Home';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import VistorCard from './components/VisitorCard/VisitorCard';
import Login from './components/Home/Login';
import { useReducer } from 'react';
import { MyUserReducer } from './configs/Reducers';
import { DispatchContext, UserContext } from './configs/Contexts';
import UpdateUser from './User/UpdateUser';
import Locker from './components/Locker/Locker';
import { useEffect } from 'react';
import { onMessage } from 'firebase/messaging';
import { generateToken, messaging } from './components/Firebase/firebase';
import toast, { Toaster } from 'react-hot-toast';
import { Toast } from 'react-bootstrap';
import Surveys from './components/Survey/Surveys';
import SurveyDetail from './components/Survey/SurveyDetail';
import UserInvoice from './components/Invoice/UserInvoices';
import Feedback from './components/Feedback/Feedback';


function App() {
  const [user, useDispatch] = useReducer(MyUserReducer, null)

  useEffect(() => {

    // generateToken()
    
    onMessage(messaging, (payload) => {
      console.log('line 27', payload)
      toast(payload.notification.body);
    })
  }, [])

  return (
    <BrowserRouter>
      <Toaster position='top-right'/>
      <UserContext.Provider value={user}>
        <DispatchContext.Provider value={useDispatch}>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/visitor_card' element={<VistorCard />} />
            <Route path='/login' element={<Login />} />
            <Route path='/update' element={<UpdateUser />} />
            <Route path='/locker' element={<Locker />} />
            <Route path='/surveys' element={<Surveys />} />
            <Route path='/surveys/:id' element={<SurveyDetail />} />
            <Route path='/invoices' element={<UserInvoice/>} />
            <Route path='/feedbacks' element={<Feedback/>}/>
          </Routes>
          
        </DispatchContext.Provider>
      </UserContext.Provider>
    </BrowserRouter>
  );
}

export default App;
