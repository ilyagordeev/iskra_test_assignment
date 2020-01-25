import React from 'react'
import ReactDOM from 'react-dom'
import 'bootstrap/dist/css/bootstrap.css'
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      updated: false
    };
    this.socket = new SockJS('http://localhost:8080/gs-guide-websocket')
    this.stompClient = Stomp.over(this.socket)
  }

    componentDidMount() {
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe('/activity/get', message => {
          this.setState({users: JSON.parse(message.body).users, updated: true})       
        })
      })
    }

    render() {

      let ready
      if (this.state.updated)
        ready = this.state.users.length === 0 ? 'Можно играть!' : 'Ждём'
      else 
        ready = "Загружаем данные"

      let readyClassString = "display-4 my-4"
      if (this.state.users.length === 0) readyClassString += " text-success"
     
      const users = this.state.users.map( user => {
        let StatusClassString = "badge badge-primary badge-pill"
        if (user.status === 'OFFLINE') StatusClassString += " bg-danger"
        if (user.status === 'AWAY') StatusClassString += " bg-warning"
        return (
          <li key={user.key} className="bg-light list-group-item d-flex justify-content-between align-items-center">
            {user.name}
            <span className={StatusClassString}>{user.status}</span> 
          </li>
        )
      })

      return (
          <div className="container">
            <div className="row">
              <div className="col-sm"></div>
              <div className="col-sm-6">
                <h1 className={readyClassString} align="center">{ready}</h1>
                <p className="lead"><ul className="list-group">{users}</ul></p>
              </div>
              <div className="col-sm"></div>
            </div>
          </div>
      )
    }
    
}


ReactDOM.render(<App />, document.getElementById('root'))

