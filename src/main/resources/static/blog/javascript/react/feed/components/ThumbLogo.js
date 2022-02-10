class ThumbLogo extends React.Component {
  render() {
      let liked = this.props.liked;
      let id = this.props.id;

      if (liked) {
        return (<img  src = "/blog/images/thumb-logo-blue-fill.svg"
                      className = "thumb-logo"
                      onClick = {this.props.likeEntry.bind(this,id,liked)}
                />);
      }
      else {
        return (<img  src = "/blog/images/thumb-logo-no-fill.svg"
                      className = "thumb-logo"
                      onClick = {this.props.likeEntry.bind(this,id,liked)}
                />);
      }
    
  }
}


