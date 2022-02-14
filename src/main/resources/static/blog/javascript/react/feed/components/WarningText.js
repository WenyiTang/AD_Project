class WarningText extends React.Component {
    render() {
        let flagged = this.props.flagged;
        if (flagged) {
            return (
            <p className = "inappropriate-text">
                You have flagged this post as inappropriate.
            </p>);
        }
        else {
            return <></>;
        }
         
    }
}