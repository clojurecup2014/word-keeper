var Translation = React.createClass({
  render: function() {
    return (
      <div className="translation">
        <h2 className="word">
          {this.props.word}
        </h2>
        {this.props.children}
      </div>
    );
  }
});
 
var TranslationList = React.createClass({
  render: function() {
    var translationNodes = this.props.data.map(function (translation) {
      return (
        <Translation word={translation.word}>
          {translation.text}
        </Translation>
      );
    });
    return (
      <div className="translationList">
        {translationNodes}
      </div>
    );
  }
});
 
var TranslationForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var author = this.refs.author.getDOMNode().value.trim();
    var text = this.refs.text.getDOMNode().value.trim();
    if (!text || !author) {
      return;
    }
    this.props.onTranslationSubmit({author: author, text: text});
    this.refs.author.getDOMNode().value = '';
    this.refs.text.getDOMNode().value = '';
    return;
  },
  render: function() {
    return (
      <form className="translationForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your word" ref="word" />
        <input type="text" placeholder="Your translation" ref="translation" />
        <input type="submit" value="Save" />
      </form>
    );
  }
});
 
var TranslationBox = React.createClass({
  loadTranslationsFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  handleTranslationSubmit: function(translation) {
    var translations = this.state.data;
    var newTranslations = translations.concat([translation]);
    this.setState({data: newTranslations});
    $.ajax({
      url: "/translations/create",
      dataType: 'json',
      type: 'POST',
      data: translation,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  getInitialState: function() {
    return { data: [] };
  },
  componentDidMount: function() {
    this.loadTranslationsFromServer();
    setInterval(this.loadTranslationsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="translationBox">
        <h1>Translations</h1>
        <TranslationList data={this.state.data} />
        <TranslationForm onTranslationSubmit={this.handleTranslationSubmit} />
      </div>
    );
  }
});
 
React.renderComponent(
  <TranslationBox url="/api/translations/1" pollInterval={2000} />,
  document.getElementById("content")
);
