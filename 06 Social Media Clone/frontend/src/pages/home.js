import React from 'react';
import {Link} from 'react-router-dom';
import './home.css'

class Home extends React.Component{
    render(){
        return(
            <div>
                <div className="half1">
                    
                </div>
                <div className="half2">
                    <div className="content">
                        <div>
                            <img src='/logo.png' className='logo left' alt='page logo'/>
                            <p className='logoname left'>Calamansee</p><br/>
                        </div><br/><br/>
                        <p className='sub'>Welcome to Calamansee, join today!</p><br/>
                        <Link to="/sign-up"><button className='signup'>Sign up</button></Link><br/>
                        <Link to="/log-in"><button className='login'>Log in</button> </Link><br/>
                    </div>
                </div>
            </div>
        );
    }
}

export default Home;