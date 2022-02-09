class ThumbLogo extends React.Component {
  render() {
      let liked = this.props.liked;

      if (liked) {
        return (<img  src = "/blog/images/thumb-logo-blue-fill.svg"
                      class = "thumb-logo"
                />);
      }
      else {
        return (<img  src = "/blog/images/thumb-logo-no-fill.svg"
                      class = "thumb-logo"
                />);
      }
    
  }
}


