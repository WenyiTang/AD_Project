
class BlogEntry extends React.Component {


  



  render() {
    let likedByActiveUser = this.props.blogEntry.likedByActiveUser;


    let flaggedByActiveUser = this.props.blogEntry.flaggedByActiveUser;

    let id = this.props.blogEntry.id;

    let dateTime = new Date(this.props.blogEntry.timeStamp);

    let dateTimeString = dateTime.toLocaleString('en-GB', { hour: 'numeric', minute: 'numeric', hour12: true, year: 'numeric', month: 'short', day: 'numeric' });



 
  
    
    return (
    <div className = "list-item">
            <a className = "pic-anchor" href = {'/blog/view/entry/' + id}>
              <img className = "pic" src = {this.props.blogEntry.imageURL}></img>
            </a>
            <div className = "title">
              <h2 >{this.props.blogEntry.title}</h2>
              <p >{'by ' + this.props.blogEntry.authorUsername}</p>
            </div>
            <p className = "date">{dateTimeString}</p>
            <p className = "description">{this.props.blogEntry.description}</p>
            <p className = "likes">
              Liked by
              <span className = "total-likes">
                {' ' + this.props.blogEntry.numberOfLikes + ' '}

              </span>
              users
            </p>

            <div className = "like-flag">
              <ThumbLogo  liked = {likedByActiveUser}
                          likeEntry = {this.props.likeEntry}
                          id = {id}
              />
              <FlagLogo   flagged = {flaggedByActiveUser}
                          id = {id}
              
              />
            </div>
            <WarningText flagged = {flaggedByActiveUser}/>
            
    </div>);

   
  }
}


