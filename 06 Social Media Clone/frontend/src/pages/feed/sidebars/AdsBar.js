import React from 'react'
import './Sidebar.css'
import './AdsBar.css'



const AdData = [
  {name: "Raycon Earbuds", image:'/raycon.jpg', content:"The sound quality of the Raycon Earbuds is just as amazing as all the other top name brands and they’re half the price. The everyday E25’s are the best ones yet."},
  {name: "Raid Shadow Legends", image:'/raid.jpg', content:"Raid Shadow Legends is one of the biggest free mobile role-playing games of 2022! Currently almost 10 million users have joined Raid, be part of the millions of users!"},
  {name: "Redbull Energy Drink", image:'/redbull.jpg', content:"Get the new Redbull energy drink! It helps revitalize your tired body and mind, ready for another day of the grind!"}
]

class Ads extends React.Component{
    render(){
        return(
            <div className='sidebar'>
                <h1>Ads</h1>
                <hr />

                {
                    AdData.map((AdData)=>{
                        return(
                            <div key={AdData.name}>
                                <h2>{AdData.name}</h2>
                                <img src={AdData.image} className='adimage' alt='advertisement'/>
                                <p className='caption'>{AdData.content}</p>
                                <hr />
                            </div>
                        )
                    })
                }
            </div>
        )
    }
}

export default Ads