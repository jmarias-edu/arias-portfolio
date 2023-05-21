import React from 'react'
import './Header.css'


class Header extends React.Component{
    render(){
        return(
            <div className='head'>
                <div>
                    <img src='/logo.png' className='logo' alt='page logo'/>
                </div>
                <div>
                    <p className='logoname'>Calamansee</p>
                </div>
                <div className="link">
                    <button>Log Out</button>
                </div>
            </div>
        )
    }
}

export default Header