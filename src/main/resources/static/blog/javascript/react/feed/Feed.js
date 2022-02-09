class Feed extends React.Component {
    state = {
        blogEntries: [],
        pageLength: 10,
        pageNo: 0,

    };


    render() {
    return <>
        <div>This is your feed</div>
        <BlogEntry/>
        </>;
    }
}


