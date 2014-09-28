/** @jsx React.DOM */

var Translation = React.createClass({
  render: function() {
    return (
      <div className="translation">
        <h2 className="translationAuthor">
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
          {translation.translation}
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
    var word = this.refs.word.getDOMNode().value.trim();
    var translation = this.refs.translation.getDOMNode().value.trim();
    if (!translation || !word) {
      return;
    }
    this.props.onTranslationSubmit({word: word, translation: translation});
    this.refs.word.getDOMNode().value = '';
    this.refs.translation.getDOMNode().value = '';
    return;
  },
  render: function() {
    return (
      <form className="translationForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your word" ref="word" />
        <input type="text" placeholder="Translation..." ref="translation" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var TranslationBox = React.createClass({
  loadTranslationsFromServer: function() {
    $.ajax({
      url: "/api/translations/" + this.props.uid, // this.props.url,
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
      url: "/api/translations/" + this.props.uid + "/create",
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
        <TranslationForm uid={this.props.uid} onTranslationSubmit={this.handleTranslationSubmit} />
      </div>
    );
  }
});
