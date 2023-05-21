import React from 'react'
import './Main.css'
import Posts from './Posts.js'

class Main extends React.Component{


    render(){
        return(
            <div className='main'>
                <Posts />
            </div>
        )
    }
}

export default Main