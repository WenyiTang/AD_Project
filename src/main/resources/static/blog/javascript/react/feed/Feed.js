class Feed extends React.Component {
    state = {
        blogEntries: [],
        pageLength: 10,
        pageNo: 0,
        activeUserId: this.props.userid,

    };
    // Method below kind of "initializes the component",
    // i.e. it runs when the page is first loaded I think
    componentDidMount() {
        fetch (
            "http://localhost:8080/api/feed/page?" +
            new URLSearchParams({
                activeUserId: this.state.activeUserId,
                pageNo: this.state.pageNo,
                pageLength: this.state.pageLength
            }), {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            })
            .then ( res => res.json() )
            .then (
                data => this.setState({blogEntries: data})
            );
    

    }

    viewMore = () => {
        //Increment pageNo
        this.setState (
            {pageNo: ++this.state.pageNo}
        );

        fetch (
            "http://localhost:8080/api/feed/page?" +
            new URLSearchParams({
                activeUserId: this.state.activeUserId,
                pageNo: this.state.pageNo,
                pageLength: this.state.pageLength
            }), {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                }
            })
            .then( res => res.json() )
            .then (data => this.setState ({
                blogEntries: [...this.state.blogEntries,...data]
            }));
        }

    likeEntry = (id,liked) => {
        if(liked) {
            fetch(
                "http://localhost:8080/api/likes/unlike?" +
                  new URLSearchParams({
                      userId: this.state.activeUserId,
                      mealEntryId: id,
                  }),
                {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                }
            ).then(
                this.setState ( {
                    blogEntries: this.state.blogEntries.map ((blogEntry) =>{
                        if(blogEntry.id === id) {
                            blogEntry.likedByActiveUser = false;
                            blogEntry.numberOfLikes--; 
                        }
                        return blogEntry;
                    })
                })
            );
        }
        else {
            fetch(
                "http://localhost:8080/api/likes/like?" +
                  new URLSearchParams({
                    userId: this.state.activeUserId,
                    mealEntryId: id,
                  }),
          
                {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                  },
                }
              ).then(
                  this.setState ( {
                      blogEntries: this.state.blogEntries.map((blogEntry) => {
                          if(blogEntry.id === id) {
                              blogEntry.likedByActiveUser = true;
                              blogEntry.numberOfLikes++;
                          }
                          return blogEntry;
                      })
                  })
              );

        }

    }
    




        
    

    render() {
        const buttonStyle = {
            display: 'block',
            margin: '0 auto',

        }
    return <>
        
        <div className = "list-view">
            <BlogEntries    blogEntries = {this.state.blogEntries}
                            likeEntry = {this.likeEntry}
            />
        </div>
        <button style = {buttonStyle} onClick = {this.viewMore}>View more</button>
        </>;
    }
}

