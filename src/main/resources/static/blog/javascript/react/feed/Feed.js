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
            }).then ( res =>res.json()
            ).then (
                data => this.setState({blogEntries: data})
            );
    

    }
    

    render() {
    return <>
        <div>Active user id = {this.state.activeUserId}. This is your feed</div>
        <BlogEntries blogEntries = {this.state.blogEntries} />
        </>;
    }
}

