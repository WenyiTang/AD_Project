class BlogEntries extends React.Component {
    render() {
      return this.props.blogEntries.map((blogEntry) => (
        <BlogEntry 
            key = {blogEntry.id}
            blogEntry = {blogEntry}
            likeEntry = {this.props.likeEntry}
        />));
    }
  }
  